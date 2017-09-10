import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import Main from './Main';

import { BrowserRouter, Link, Route } from 'react-router-dom';

const Root = () => {
  return (
    <BrowserRouter>
      <div>
        <Route path="/" component={Main} />
      </div>
    </BrowserRouter>
  )
}

ReactDOM.render(<Root/>, document.getElementById('root'));