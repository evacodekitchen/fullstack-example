import React from 'react'
import Card from 'react-bootstrap/Card'
import Button from 'react-bootstrap/Button'
import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'
import Container from 'react-bootstrap/Container'
import {SearchContext} from "../context/SearchContext";
import {useContext} from "react"
import {useEffect} from "react"
import {useState} from "react"
import axios from "axios";

const PropertyList = () => {

    const {searchedCity, setSearchedCity} = useContext(SearchContext)

    const [properties, setProperties] = useState([])

    useEffect(() => {
        alert("change" + searchedCity)
        let url = "http://localhost:8080/api/v1/properties"
        if (searchedCity) {
            url = url + "?city=" + searchedCity
        }
        axios.get(url).then(response => {
            setProperties(response.data)
        }).catch(e => {
            console.log(e)
        })
    }, [searchedCity])

    const cards = properties.map(property =>
        <Col key={property.id}>
            <Card style={{width: '18rem'}}>
                <Card.Img variant="top" src={"data:image/png;base64," + property.picture}/>
                <Card.Body>
                    <Card.Title>{property.type + " for " + property.saleOrRent}</Card.Title>
                    <Card.Text>{property.price + " Euro"}</Card.Text>
                    <Card.Text>{property.city}</Card.Text>
                    <Card.Text className={"text-truncate"}>{property.description}</Card.Text>
                    <Button variant="primary">Read more</Button>
                </Card.Body>
            </Card>
        </Col>
    )
    return <Container><Row xs={20}>{cards}</Row></Container>
}

export default PropertyList;