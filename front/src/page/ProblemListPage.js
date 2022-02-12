import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Button, Table } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import ProblemTableBody from '../component/ProblemTableBody';

function ProblemListPage() {

  const [problems, setProblems] = useState();

  const ProblemList = async () => {
    await axios.get("") // update url
      .then(res => {
        setProblems(...res.data);
      })
      .then(err => {
        alert(err);
      })
  }

  useEffect(() => {
    ProblemList();
  }, []);

  return <div>
    <Table striped bordered hover size="sm">
      <thead>
        <tr>
          <th>index</th>
          <th>title</th>
          <th>categorys</th>
        </tr>
      </thead>
      <tbody>
      {/* {problems.map((problem, index) => {
        <ProblemTableBody key= {index} problem = {problem}/>       
      })} */}
      </tbody>
    </Table>
    <Link to='/problems/insert'><Button >insert problem</Button></Link>
  </div>;
}

export default ProblemListPage;
