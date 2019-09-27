import React from 'react';
import REPHeader from "../components/Header";
import PropertyList from "../components/PropertyList";
import {SearchContextProvider} from "../context/SearchContext";

const SearchRealEstatesPage = () => {
    return (
        <SearchContextProvider>
            <REPHeader/>
            <PropertyList></PropertyList>
        </SearchContextProvider>
    );
};
export default SearchRealEstatesPage;
