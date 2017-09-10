import React from 'react';
import { render } from 'react-dom';
import loginForm from './loginForm';

// class login extends React.Component {
//   constructor(props) {
//     super(props);
//     this.state = {
//       loginToken: localStorage.getItem('token')
//     }

//   }

//   componentDidMount() {
//   	console.log("Login Mount");
//   	console.log(this);
//   }

//   render(){
//   	return (<div>TESTING</div>)
//   }

//   //   if(this.state.loginToken === void(0)){

//   //     return (<a href="/login">Login</a>)

//   //   } else {

//   //     return(
//   //       <div>
//   //         <img src="%PUBLIC_URL%/favicon48.png" />
//   //       </div>)

//   //   }

// }

const login = (props) => {
  return (
    <div className="top">
    {loginForm}
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


export default login;