import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Button, Col, Form, Table } from 'react-bootstrap';
import { Link, useNavigate, useParams } from 'react-router-dom';

const TestCaseList = () => {

    const [testcases, setTestcases] = useState();
    const { problemId } = useParams();
    const [input, setInput] = useState();
    const [output, setOutput] = useState();
    const [flag, setFlag] = useState(false);
    const navigate = useNavigate();

    const handleInput = (e) => {
        setInput(e.target.value);
    }

    const handleOutput = (e) => {
        setOutput(e.target.value);
    }

    const insertTestcase = async () => {
        await axios.post("http://localhost:8080/testcase/" + problemId, {
            parameter: input,
            expectedData: output
        }, {
            headers: {
                'Authorization': localStorage.getItem('Authorization')
            }
        })
            .then((res) => {

            })
            .catch((err) => {
                alert(err);
            })
    }
    const getTestcases = async () => {
        axios.get("http://localhost:8080/testcase/" + problemId)
            .then(res => {
                setTestcases(res.data);
            })
            .catch(err => {
                alert(err);
            })
    }

    const handleFlag = () => {
        setFlag(true);
    }

    useEffect(() => {
        getTestcases()
    }, [])

    return (
        <div>
            <Col md={{ span: 8, offset: 2 }}>
                <Table striped bordered hover size="sm">
                    <thead>
                        <tr>
                            <th>index</th>
                            <th>input</th>
                            <th>output</th>
                        </tr>
                    </thead>
                    <tbody>
                        {testcases === undefined ? "sadas" : testcases.map((testcase, index) => {
                            return (<tr>
                                <td>{testcase.id}</td>
                                <td>{testcase.parameter}</td>
                                <td>{testcase.expectedData}</td>
                            </tr>
                            )
                        })}
                    </tbody>
                </Table>
            </Col>
            {flag === false ? <Button onClick={handleFlag}>테스트 케이스 추가하기</Button> :
                <div>
                    <Form>
                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>input</Form.Label>
                            <Form.Control onChange={handleInput} />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>output</Form.Label>
                            <Form.Control onChange={handleOutput} />
                        </Form.Group>
                        <Button variant="primary" onClick={insertTestcase}>
                            Submit
                        </Button>
                    </Form>
                </div>}
        </div>
    );
};

export default TestCaseList;