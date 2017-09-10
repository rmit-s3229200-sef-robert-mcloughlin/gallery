import React from 'react';
import { render } from 'react-dom';


const loginForm = (props) => {
  
  return (
    <div className="top">
      <h1>
        Catch
        <span className="ofThe">
          <span className="of">of</span>
          <span className="the">the</span>
        </span>
        Day
      </h1>
      <h3 className="tagline"><span>{props.tagline}</span></h3>
    </div>
  )
}


export default loginForm;