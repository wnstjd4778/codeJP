import React from 'react';
import { Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';

function MainPage() {
  return <div>

    <Link to='/problems'><Button>problem</Button></Link>
  </div>;
}

export default MainPage;
