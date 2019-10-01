import React from 'react'
import Card from 'react-bootstrap/Card'
import {Link} from "react-router-dom";
import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'
import Container from 'react-bootstrap/Container'
import {SearchContext} from "../context/SearchContext";
import Pagination from "react-bootstrap/Pagination"
import {useContext} from "react"
import {useEffect} from "react"
import {useState} from "react"
import axios from "axios";

const PropertyList = () => {

    const {searchedCity, setSearchedCity} = useContext(SearchContext)

    const [properties, setProperties] = useState([])

    const [activePageIndex, setActivePageIndex] = useState(0)

    const [nrOfPages, setNrOfPages] = useState(0)

    const pageSize = 6
    const p = [Array(nrOfPages).keys()]
    const pages = (
        Array(nrOfPages).fill().map((v, i) => i).map(pageIndex =>
            <Pagination.Item name="pageIndex" key={pageIndex} active={pageIndex === activePageIndex}
                             onClick={(event) => {
                                 turnPage(event)
                             }}>
                {pageIndex + 1}
            </Pagination.Item>
        )
    )


    const turnPage = (event) => {
        const pageIndex = event.target.text

        setActivePageIndex(pageIndex - 1)
    }


    useEffect(() => {
        let url = "http://localhost:8080/api/v1/properties"

        url = url + `?size=${pageSize}&page=${activePageIndex}`

        if (searchedCity) {
            setActivePageIndex(0)
            url = url + "&city=" + searchedCity
        }

        axios.get(url).then(response => {
            setNrOfPages(response.data.totalNrOfPages)
            setProperties(response.data.propertyList)
        }).catch(e => {
            console.log(e)
        })
    }, [searchedCity, activePageIndex])

    const cards = properties.map(property =>
        <Col key={property.id} className="mt-4" md={4}>
            <Card style={{width: '18rem'}}>
                <Card.Img variant="top" src={"data:image/png;base64," + property.picture}/>
                <Card.Body>
                    <Card.Title>{property.type + " for " + property.saleOrRent}</Card.Title>
                    <Card.Text>{property.price + " Euro"}</Card.Text>
                    <Card.Text>{property.city}</Card.Text>
                    <Card.Text className={"text-truncate"}>{property.description}</Card.Text>
                    <Link to={`/properties/${property.id}`}>View details</Link>
                </Card.Body>
            </Card>
        </Col>
    )
    return <Container>
        <Row>{cards}</Row>
        <Row md={12} className={"mt-4 justify-content-md-center"}>

                <Pagination>
                    {pages}
                </Pagination>


        </Row>
    </Container>
}

export default PropertyList;