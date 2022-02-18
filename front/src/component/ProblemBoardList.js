import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Button, Col, Table } from 'react-bootstrap';
import { Link, useParams } from 'react-router-dom';

const ProblemBoardList = () => {

  const [boards, setBoards] = useState();
  const { problemId } = useParams();

  const getBoards = async () => {
    await axios.get("http://localhost:8080/problems/" + problemId + "/boards/all")
      .then(res => {
        setBoards(res.data);
      })
      .catch(err => {
        alert(err);
      })
  }

  useEffect(() => {
    getBoards();
  }, []);

  return (
    <div>
      <Col md={{ span: 8, offset: 2 }}>
        <Table striped bordered hover size="sm">
          <thead>
            <tr>
              <th>index</th>
              <th>title</th>
              <th>hits</th>
              <th>link</th>
            </tr>
          </thead>
          <tbody>
            {boards === undefined ? "sadas" : boards.map((board, index) => {
              return (<tr>
                <td>{index + 1}</td>
                <td>{board.title}</td>
                <td>{board.hits}</td>
                <td><Link to={"/problems/" + problemId + "/boards/" + board.id}><Button>link</Button></Link></td>
              </tr>
              )
            })}
          </tbody>
        </Table>
      </Col>
    </div>
  );
};

export default ProblemBoardList;