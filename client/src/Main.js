import React, { Component } from 'react';

class Main extends Component {
  render() {
    return (
      <nav className="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <a className="navbar-brand" href="#"><img src="https://www.royalacademy.org.uk/assets/ra-logo-white-5c387a2950d1d5db412204768321029b.svg"  style={{height: '2em'}} /></a>
        <button className="navbar-toggler d-lg-none" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarsExampleDefault">
          <ul className="navbar-nav mr-auto">
            <li className="nav-item active">
            		LOGIN
            </li>
          </ul>
          SEARCH
        </div>
      </nav>
    )
  }
}

export default Main;