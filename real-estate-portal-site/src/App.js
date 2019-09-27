import React from 'react';
import SearchRealEstatesPage from './pages/SearchRealEstatesPage'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import PropertyDetails from "./pages/PropertyDetailsPage";

const NoMatchRoute = () => <div>404 Page</div>;

const App = () => {
    return (
        <Router>
            <Switch>
                <Route path="/" exact component={SearchRealEstatesPage}/>
                <Route path="/properties/:propertyId" exact component={PropertyDetails}/>
                <Route component={NoMatchRoute}/>
            </Switch>
        </Router>
    );

};
export default App;
