import React from 'react'
import Navbar from 'react-bootstrap/Navbar'
import Nav from 'react-bootstrap/Nav'
import NavDropdown from 'react-bootstrap/NavDropdown'
import Form from 'react-bootstrap/Form'
import FormControl from 'react-bootstrap/FormControl'
import Button from 'react-bootstrap/Button'
import Container from 'react-bootstrap/Container'
import {SearchContext} from "../context/SearchContext";
import {useContext} from "react"
import {Link} from "react-router-dom";

const ActionHeader = () => {

    const searchContext = useContext(SearchContext)

    const {searchedCity, setSearchedCity} = searchContext

    let typedSearchedCity = ""

    const updateTypedSearchedCity = (typedValue) => {
        typedSearchedCity = typedValue
    }

    const updateSearchedCityInContext = (event) => {
        event.preventDefault()
        setSearchedCity(typedSearchedCity)
    }

    return <Navbar bg="dark" variant="dark" expand="lg">
        <Container>
            <Navbar.Brand href="#home">Real Estate Portal</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav"/>
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                    <Nav.Link as={Link} to={"/properties"}>Home</Nav.Link>
                </Nav>
                <Form inline onSubmit={(event) => updateSearchedCityInContext(event)}>
                    <FormControl onChange={(event) => updateTypedSearchedCity(event.target.value)} type="text"
                                 placeholder="Search in city" className="mr-sm-2"/>
                </Form>
                <Link to={"/properties/new"}> <Button variant="warning" className="ml-md-4">Add new property</Button></Link>
            </Navbar.Collapse></Container>
    </Navbar>
};
export default ActionHeader;