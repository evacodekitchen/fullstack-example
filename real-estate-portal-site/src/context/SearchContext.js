import React, {createContext, useState} from "react";
import PropTypes from "prop-types";

export const Context = createContext({});

export const Provider = props => {
    // Initial values are obtained from the props
    const [searchedCity, setSearchedCity] = useState("");

    const searchContext = {
        searchedCity,
        setSearchedCity
    };

    // pass the value in provider and return
    return <Context.Provider value={searchContext}>{props.children}</Context.Provider>;
};

export const {Consumer} = Context;

Provider.propTypes = {
    searchedCity: PropTypes.string
};

Provider.defaultProps = {
    searchInCity: ""
};

export {
    Context as SearchContext,
    Provider as SearchContextProvider,
    Consumer as SearchContextConsumer
}

