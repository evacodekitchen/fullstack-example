import React from 'react';
import ActionHeader from "../components/ActionHeader";
import PropertyList from "../components/PropertyList";
import {SearchContextProvider} from "../context/SearchContext";

const SearchRealEstatesPage = () => {
    return (
        <SearchContextProvider>
            <ActionHeader/>
            <PropertyList></PropertyList>
        </SearchContextProvider>
    );
};
export default SearchRealEstatesPage;
