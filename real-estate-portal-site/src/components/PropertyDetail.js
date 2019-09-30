import React from 'react';
import Card from 'react-bootstrap/Card'

const PropertyDetail = ({property}) => {

    return <Card className="text-center">
        <Card.Header>{property.city + ", " + property.street} street</Card.Header>
        <Card.Body>
            <Card.Title>{property.type} for {property.saleOrRent}, {property.price} EURO</Card.Title>
            <Card.Img variant="bottom" src={"data:image/png;base64," + property.picture}/>
            <Card.Text>{property.description}</Card.Text>
        </Card.Body>
        <Card.Footer className="text-muted">Average price in the area: 2000</Card.Footer>
    </Card>
};
export default PropertyDetail;