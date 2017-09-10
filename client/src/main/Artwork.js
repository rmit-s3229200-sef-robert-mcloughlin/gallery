import React from 'react';

// import {Table} from 'react-bootstrap';

function getArtworks(index) {
  var url = "http://localhost:8080/artwork/";
  if(!!index){
    url = url + index;
  }
  return fetch(url)
    .then((response) => response.json())
    .then((responseJson) => {
      if(!!index){
        return [responseJson]
      } else {
        return responseJson
      }
    })
    .catch((error) => {
      return error
    });
}

class Artwork extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoading: true
    }

  }

  componentDidMount() {
  	var index = this.props.params.id;
    
    getArtworks(index).then((res, err) => {
    	if(res[0].error){
    		this.setState({isErr: true});
    		return err
    	}

          this.setState({
            artworks: res,
            isLoading: false
          });
    });

  }
  render() {
  	if(this.state.isErr){
  		return (<div>Errors Has Occured</div>)
  	}

    if(this.state.isLoading){

      return (<div>Loading.</div>)

    } else {

      return(
        <div>
          {this.state.artworks.map(function(artwork){
            return(<div key={artwork.id}><h1>{artwork.name}</h1><p>{artwork.description}</p></div>)
          })}
        </div>)

    }

  }
}



export default Artwork;

// export default React.createClass({
//   render() {
//     return (
//       <div>
//         <h1>React Router Tutorial</h1>
//         <ul role="nav">
//           <li><Link to="/about">About</Link></li>
//           <li><Link to="/repos">Repos</Link></li>
//         </ul>
//       </div>
//     )
//   }
// })
