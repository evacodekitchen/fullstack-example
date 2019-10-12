import React from 'react';
import SearchRealEstatesPage from './pages/SearchRealEstatesPage'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import PropertyDetails from "./pages/PropertyDetailsPage";
import PropertyCreatorPage from "./pages/PropertyCreatorPage";

const NoMatchRoute = () => <div>404 Page not found</div>;

const App = () => {
    return (
        <Router>
            <Switch>
                <Route path="/properties" exact component={SearchRealEstatesPage}/>
                <Route path="/properties/new" component={PropertyCreatorPage}/>
                <Route path="/properties/:propertyId" component={PropertyDetails}/>
                <Route component={NoMatchRoute}/>
            </Switch>
        </Router>
    );
};
export default App;
