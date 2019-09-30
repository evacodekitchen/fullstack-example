import React from 'react';
import {useState} from "react"
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import Alert from "react-bootstrap/Alert";
import Feedback from "react-bootstrap/Feedback";
import FormCheck from "react-bootstrap/FormCheck";
import ActionHeader from "../components/ActionHeader";

const PropertyCreatorPage = () => {

    const [loading, setLoading] = useState(false)
    const [error, setError] = useState(false)
    const [validated, setValidated] = useState(false);
    const [createdPropId, setCreatedPropId] = useState(null)

    const handleSubmit = event => {
        const form = event.currentTarget;
        event.preventDefault();
        event.stopPropagation();
        setValidated(true);

        if (form.checkValidity()) {
            const formData = new FormData(event.target);
            postData(formData)
        }

    };

    const postData = ((formData) => {
        const config = {
            headers: {'Content-Type': 'multipart/form-data'}
        }
        let url = `http://localhost:8080/api/v1/properties`
        setLoading(true)
        setError(false)
        const options = {
            method: 'POST',
            body: formData
        }

        fetch(url, options)
            .then(response => response.json())
            .then(response => {
                setLoading(false)
                setCreatedPropId(response.id)
            })
            .catch(e => {
                setLoading(false)
                setError(true)
                console.log(e)
            })
    })


    const form = <Form noValidate validated={validated} onSubmit={(event) => handleSubmit(event)}>
        <Form.Row>
            <Form.Group as={Col} md={6} controlId="typeGroup">
                <Form.Label>Property type</Form.Label>
                <Form.Check>
                    <FormCheck.Input required type="radio" value="HOUSE" name="type" id="houseRadio"/>
                    <FormCheck.Label>HOUSE</FormCheck.Label>
                </Form.Check>
                <Form.Check>
                    <FormCheck.Input required type="radio" value="FLAT" name="type" id="flatRadio"/>
                    <FormCheck.Label>FLAT</FormCheck.Label>
                    <Feedback type="invalid">Please choose a property type!</Feedback>
                </Form.Check>
            </Form.Group>
            <Form.Group as={Col} md={6} controlId="saleOrRentGroup">
                <Form.Label>Property type</Form.Label>
                <Form.Check>
                    <FormCheck.Input required type="radio" value="SALE" name="saleOrRent" id="saleRadio"/>
                    <FormCheck.Label>SALE</FormCheck.Label>
                </Form.Check>
                <Form.Check>
                    <FormCheck.Input required type="radio" value="RENT" name="saleOrRent" id="rentRadio"/>
                    <FormCheck.Label>RENT</FormCheck.Label>
                    <Feedback type="invalid">Please choose if you want to sale or rent the property!</Feedback>
                </Form.Check>
            </Form.Group>
        </Form.Row>
        <Form.Row>
            <Form.Group as={Col} md={4} controlId="cityGroup">
                <Form.Label>City</Form.Label>
                <Form.Control name="city" placeholder="City" required/>
                <Form.Control.Feedback type="invalid">
                    Please provide a valid city.
                </Form.Control.Feedback>
            </Form.Group>
            <Form.Group as={Col} md={4} controlId="streetGroup">
                <Form.Label>Street</Form.Label>
                <Form.Control name="street" placeholder="Street" required/>
                <Form.Control.Feedback type="invalid">
                    Please provide a valid street.
                </Form.Control.Feedback>
            </Form.Group>

            <Form.Group as={Col} md={4} controlId="priceGroup">
                <Form.Label>Price in Euro</Form.Label>
                <Form.Control type="number" name="price" placeholder="Price" required/>
                <Form.Control.Feedback type="invalid">
                    Please provide a number as price.
                </Form.Control.Feedback>
            </Form.Group>
        </Form.Row>
        <Form.Row>
            <Form.Group controlId="descriptionGroup">
                <Form.Label>Description</Form.Label>
                <Form.Control as="textarea" name="description" placeholder="Description" required/>
                <Form.Control.Feedback type="invalid">
                    Please provide a description min 10 characters, max 100 characters long.
                </Form.Control.Feedback>
            </Form.Group>
        </Form.Row>
        <Form.Row>
            <Form.Group controlId="pictureGroup">
                <Form.Label>Picture</Form.Label>
                <Form.Control name="picture" type="file" required/>
                <Form.Control.Feedback type="invalid">
                    Please select a picture from your computer.
                </Form.Control.Feedback>
            </Form.Group>
        </Form.Row>
        <Form.Group>
            <Button type="submit">Add</Button>
        </Form.Group>
    </Form>

    return (
        <div>
            <ActionHeader searchEnabled={false}/>
            <Container>
                <Card className="mt-4 p-4">
                    {loading && (
                        <Alert key="alert-loading" variant="info">
                            Adding new property...
                        </Alert>
                    )}
                    {createdPropId && (
                        <Alert key="alert-loading" variant="success">
                            Property created with id {createdPropId}
                        </Alert>
                    )}
                    {error && (
                        <Alert key="alert-error" variant="danger">
                            Oops, something went wrong, please try again!
                        </Alert>
                    )}
                    {form}
                </Card>
            </Container>
        </div>
    )
};
export default PropertyCreatorPage;