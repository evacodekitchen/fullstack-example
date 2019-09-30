import React from 'react';
import {useEffect} from "react"
import {useState} from "react"
import axios from "axios";
import PropertyDetail from "../components/PropertyDetail";
import Container from "react-bootstrap/Container";
import {Link} from "react-router-dom";
import Alert from "react-bootstrap/Alert";
import ActionHeader from "../components/ActionHeader";

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
            setLoading(false)
            setProperty(response.data)
        }).catch(e => {
            setLoading(false)
            setError(true)
            console.log(e)
        })

    }, [propertyId])


    return (
        <div>
            <ActionHeader searchEnabled={false}/>
            <Container className="mt-4">
                {loading && (
                    <Alert key="alert-loading" variant="info">
                        Loading property detail for property ID: <strong>{propertyId}</strong> ...
                    </Alert>
                )}
                {error && (
                    <Alert key="alert-error" variant="danger">
                        Oops, something went wrong, please try again!
                    </Alert>
                )}
                {property && (
                    <PropertyDetail property={property}/>
                )}
            </Container>
        </div>
    )
};
export default PropertyDetailsPage;