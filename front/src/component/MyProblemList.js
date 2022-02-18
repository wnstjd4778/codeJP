import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Table } from 'react-bootstrap';

const MyProblemList = () => {
  const [problems, setProblems] = useState();

  const getProblem = async () => {
    await axios.get('http://localhost:8080/problem/me', {
      headers: {
        'Authorization': localStorage.getItem('Authorization')
      }
    })
      .then(res => {
        console.log(res);
        setProblems(res.data);
      })
      .catch(err => {
        console.log(err);
      })
  };

  useEffect(() => {
    getProblem();
  }, []);

  return <div>
    <Col md={{ span: 8, offset: 2 }}>
      <Table striped bordered hover size="sm">
        <thead>
          <tr>
            <th>index</th>
            <th>title</th>
            <th>categorys</th>
            <th>testCase</th>
          </tr>
        </thead>
        <tbody>
          {problems === undefined ? "sadas" : problems.map((problem, index) => {
            return (<tr>
              <td>{index + 1}</td>
              <td>{problem.title}</td>
              <td>{problem.category}</td>
              <td><Link to={'/testcase/' + problem.id}><Button>testcase</Button></Link></td>
            </tr>
            )
          })}
        </tbody>
      </Table>
      <Link to='/problems/insert'><Button >insert problem</Button></Link>
    </Col>
  </div>;
}

export default MyProblemList;
