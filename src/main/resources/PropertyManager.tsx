import { useState, useEffect } from 'react'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Textarea } from "@/components/ui/textarea"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Label } from "@/components/ui/label"
import { Badge } from "@/components/ui/badge"
import { toast, ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import { Home, DollarSign, Maximize, FileText } from 'lucide-react'

interface Property {
  id?: number
  address: string
  price: number
  size: number
  description: string
}

export default function PropertyManager() {
  const [properties, setProperties] = useState<Property[]>([])
  const [currentProperty, setCurrentProperty] = useState<Property>({ address: '', price: 0, size: 0, description: '' })
  const [isEditing, setIsEditing] = useState(false)

  useEffect(() => {
    fetchProperties()
  }, [])

  const fetchProperties = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/properties')
      if (!response.ok) throw new Error('Failed to fetch properties')
      const data = await response.json()
      setProperties(data)
    } catch (error) {
      toast.error('Error fetching properties')
    }
  }

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target
    setCurrentProperty(prev => ({ ...prev, [name]: name === 'price' || name === 'size' ? Number(value) : value }))
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      const url = isEditing ? `http://localhost:8080/api/properties/${currentProperty.id}` : 'http://localhost:8080/api/properties'
      const method = isEditing ? 'PUT' : 'POST'
      const response = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(currentProperty)
      })
      if (!response.ok) throw new Error('Failed to save property')
      toast.success(isEditing ? 'Property updated successfully' : 'Property created successfully')
      setCurrentProperty({ address: '', price: 0, size: 0, description: '' })
      setIsEditing(false)
      fetchProperties()
    } catch (error) {
      toast.error('Error saving property')
    }
  }

  const handleEdit = (property: Property) => {
    setCurrentProperty(property)
    setIsEditing(true)
  }

  const handleDelete = async (id: number) => {
    if (!window.confirm('Are you sure you want to delete this property?')) return
    try {
      const response = await fetch(`http://localhost:8080/api/properties/${id}`, { method: 'DELETE' })
      if (!response.ok) throw new Error('Failed to delete property')
      toast.success('Property deleted successfully')
      fetchProperties()
    } catch (error) {
      toast.error('Error deleting property')
    }
  }

  return (
    <div className="container mx-auto p-4 bg-gradient-to-r from-blue-50 to-indigo-50 min-h-screen">
      <h1 className="text-3xl font-bold mb-6 text-center text-primary">Property Manager</h1>
      <Card className="mb-8">
        <CardHeader>
          <CardTitle>{isEditing ? 'Edit Property' : 'Add New Property'}</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="address">Address</Label>
              <div className="flex">
                <span className="inline-flex items-center px-3 rounded-l-md border border-r-0 border-gray-300 bg-gray-50 text-gray-500">
                  <Home className="h-5 w-5" />
                </span>
                <Input
                  id="address"
                  type="text"
                  name="address"
                  value={currentProperty.address}
                  onChange={handleInputChange}
                  placeholder="123 Main St, City, Country"
                  required
                  className="rounded-l-none"
                />
              </div>
            </div>
            <div className="space-y-2">
              <Label htmlFor="price">Price</Label>
              <div className="flex">
                <span className="inline-flex items-center px-3 rounded-l-md border border-r-0 border-gray-300 bg-gray-50 text-gray-500">
                  <DollarSign className="h-5 w-5" />
                </span>
                <Input
                  id="price"
                  type="number"
                  name="price"
                  value={currentProperty.price}
                  onChange={handleInputChange}
                  placeholder="100000"
                  required
                  min="0"
                  className="rounded-l-none"
                />
              </div>
            </div>
            <div className="space-y-2">
              <Label htmlFor="size">Size (sqft)</Label>
              <div className="flex">
                <span className="inline-flex items-center px-3 rounded-l-md border border-r-0 border-gray-300 bg-gray-50 text-gray-500">
                  <Maximize className="h-5 w-5" />
                </span>
                <Input
                  id="size"
                  type="number"
                  name="size"
                  value={currentProperty.size}
                  onChange={handleInputChange}
                  placeholder="1000"
                  required
                  min="0"
                  className="rounded-l-none"
                />
              </div>
            </div>
            <div className="space-y-2">
              <Label htmlFor="description">Description</Label>
              <div className="flex">
                <span className="inline-flex items-center px-3 rounded-l-md border border-r-0 border-gray-300 bg-gray-50 text-gray-500">
                  <FileText className="h-5 w-5" />
                </span>
                <Textarea
                  id="description"
                  name="description"
                  value={currentProperty.description}
                  onChange={handleInputChange}
                  placeholder="A beautiful property with..."
                  required
                  className="rounded-l-none"
                />
              </div>
            </div>
            <Button type="submit" className="w-full">{isEditing ? 'Update' : 'Create'} Property</Button>
          </form>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Property Listings</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Address</TableHead>
                <TableHead>Price</TableHead>
                <TableHead>Size</TableHead>
                <TableHead>Description</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {properties.map((property) => (
                <TableRow key={property.id}>
                  <TableCell>
                    <Badge variant="outline" className="font-normal">
                      <Home className="h-4 w-4 mr-2" />
                      {property.address}
                    </Badge>
                  </TableCell>
                  <TableCell>
                    <Badge variant="secondary" className="font-normal">
                      <DollarSign className="h-4 w-4 mr-2" />
                      {property.price.toLocaleString()}
                    </Badge>
                  </TableCell>
                  <TableCell>
                    <Badge variant="secondary" className="font-normal">
                      <Maximize className="h-4 w-4 mr-2" />
                      {property.size} sqft
                    </Badge>
                  </TableCell>
                  <TableCell>
                    <Badge variant="outline" className="font-normal">
                      <FileText className="h-4 w-4 mr-2" />
                      {property.description.substring(0, 50)}...
                    </Badge>
                  </TableCell>
                  <TableCell>
                    <div className="space-x-2">
                      <Button onClick={() => handleEdit(property)} size="sm" variant="outline">Edit</Button>
                      <Button onClick={() => handleDelete(property.id!)} size="sm" variant="destructive">Delete</Button>
                    </div>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
      <ToastContainer />
    </div>
  )
}