import React from 'react';
import login from './authentication/login'

function getArtworks() {
  var url = "http://localhost:8080/artwork/";
  return fetch(url, { 
   method: 'get', 
   headers: {
     'Authorization': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6InBhc3N3b3JkIiwicm9sZSI6ImFkbWluIiwiaXNzIjoiYXV0aDAiLCJ1c2VybmFtZSI6ImFkbWluVXNlciJ9.wlRg7zVc3GWPMUyU5tx6VeLiJsFGzWf1HZNfKGzzmIs', 
     'Content-Type': 'application/x-www-form-urlencoded'
   }
  })
    .then((response) => response.json())
    .then((responseJson) => {
        return responseJson
    })
    .catch((error) => {
      console.error(error);
    });

}

class Gallery extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoading: true
    }

  }

  componentDidMount() {
    getArtworks(1).then((res) => {
          this.setState({
            artworks: res,
            isLoading: false
          });
    });

  }
  render() {

    if(this.state.isLoading){

      return (<div>Loading.</div>)

    } else {

      return (
        <div>
          {this.state.artworks.map(function(artwork){
            return(<div key={artwork.id}><h1>{artwork.name}</h1><p>{artwork.description}</p></div>)
          })}
        </div>
      )

    }

  }
}



export default Gallery;