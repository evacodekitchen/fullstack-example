import React, {useState, useEffect} from 'react';
import REPHeader from "./components/Header";
import PropertyList from "./components/PropertyList";
import axios from 'axios'

const App = () => {

    const [properties, setProperties] = useState([])
    useEffect(() => {

        axios.get("http://localhost:8080/api/v1/properties").then(response => {
            setProperties(response.data)
        }).catch( e=> {
            console.log(e)
        })
    }, [])

    return (
        <div>
            <REPHeader/>
            <PropertyList properties={properties}></PropertyList>
        </div>
    );
};
export default App;
