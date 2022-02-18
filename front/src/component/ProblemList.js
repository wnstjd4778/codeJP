import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Button, Col, FormControl, InputGroup, Row, Table } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import '../App.css'

function ProblemList(props) {
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
        <Col md={{ span: 3, offset: 9 }}>
        <InputGroup className="mb-3">
    <FormControl
      placeholder="검색"
      aria-label="Recipient's username"
      aria-describedby="basic-addon2"
    />
    <Button variant="outline-secondary" id="button-addon2">
      Button
    </Button>
  </InputGroup>
  </Col>

        <Col md={{ span: 8, offset: 2 }}>
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
                        return (<tr>
                            <td>{index + 1}</td>
                            <td>{problem.title}</td>
                            <td>{problem.category}</td>
                            <td><Link to={'/problems/' + problem.id}><Button>problem</Button></Link></td>
                        </tr>
                        )
                    })}
                </tbody>
            </Table>
        </Col>
    </div>;
}

export default ProblemList;
