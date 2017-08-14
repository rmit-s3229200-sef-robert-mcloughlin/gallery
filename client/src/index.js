import React from 'react';
import { render } from 'react-dom'
import Gallery from './main/Gallery';
import './css/App.css';
import './css/grails.css';
import './css/main.css';

import { Router, Route, hashHistory } from 'react-router'

// ReactDOM.render(
//   <Gallery />,
//   document.getElementById('root')
// );

render((
  <Router history={hashHistory}>
    <Route path="/" component={Gallery}/>
  </Router>
), document.getElementById('root'))
