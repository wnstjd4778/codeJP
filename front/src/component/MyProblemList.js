import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';
import { Button, Table } from 'react-bootstrap';
import ProblemTableBody from './ProblemTableBody';
import MyProblemTableBody from './MyProblemTableBody';

const MyProblemList = () => {
    const [problems, setProblems] = useState();

    const getProblem = async () => {
        await axios.get('http://localhost:8080/problem/me',{
            headers: {
                'Authorization' : localStorage.getItem('Authorization')
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
          return (<MyProblemTableBody key={index} problem={problem} />
          )
        })}
      </tbody>
    </Table>
    <Link to='/problems/insert'><Button >insert problem</Button></Link>
  </div>;
}

export default MyProblemList;
