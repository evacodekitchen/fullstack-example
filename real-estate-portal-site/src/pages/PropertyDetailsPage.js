import React from 'react';
import {useEffect} from "react"
import {useState} from "react"
import axios from "axios";
import PropertyDetail from "../components/PropertyDetail";
import Container from "react-bootstrap/Container";
import {Link} from "react-router-dom";

const PropertyDetailsPage = ({match}) => {
    const {
        params: {propertyId},
    } = match

    const [property, setProperty] = useState(null)
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState(false)

    useEffect(() => {
        let url = `http://localhost:8080/api/v1/properties/${propertyId}`
        setLoading(true)
        setError(false)
        axios.get(url).then(response => {
            setProperty(response.data)
        }).catch(e => {
            setError(true)
            console.log(e)
        })
        setLoading(false)
    }, [propertyId])


    return (
        <Container>
            <Link to={`/`}>Go back to search properties</Link>
            {loading && (
                <div style={{color: `green`}}>
                    loading book detail for book ID: <strong>{propertyId}</strong>
                </div>
            )}
            {error && (
                <div style={{color: `red`}}>
                    some error occurred, while fetching api
                </div>
            )}
            {property && (
                <PropertyDetail property={property}/>
            )}
        </Container>
    )
};
export default PropertyDetailsPage;