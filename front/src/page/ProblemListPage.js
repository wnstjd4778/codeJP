import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Button, Table } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import ProblemTableBody from '../component/ProblemTableBody';

function ProblemListPage() {

  const [problems, setProblems] = useState();
  const navigate = useNavigate();
  const ProblemList = async () => {
    await axios.get("http://localhost:8080/problem")
      .then(res => {
        console.log(res.data);
        setProblems(res.data.slice());
        console.log(problems);
      })
      .catch(err => {
        alert(err + "에러ㅗ가 났습니다.");
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
          <th>link</th>
        </tr>
      </thead>
      <tbody>
        {problems === undefined ? "sadas" : problems.map((problem, index) => {
          return (<ProblemTableBody key={index} problem={problem} />
          )
        })}
      </tbody>
    </Table>
  </div>;
}

export default ProblemListPage;
