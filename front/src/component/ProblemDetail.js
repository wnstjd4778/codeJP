import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, FloatingLabel, Form, Nav, Row } from 'react-bootstrap';
import '../App.css';
const ProblemDetail = (props) => {


    const { problemId } = useParams();
    const [problem, setProblem] = useState();
    const getProblem = async () => {
        await axios.get('http://localhost:8080/problem/' + problemId)
            .then(res => {
                console.log(res);
                setProblem(res.data);
            })
            .catch(err => {
                console.log(err);
            })
    };

    useEffect(() => {
        getProblem();
    }, []);
    return <div>
        {problem === undefined ? "asdsa" : <div>
            <br />
            <Nav justify variant="tabs" defaultActiveKey="/home">
                <Nav.Item>
                    <Nav.Link href="">{problem.id + "번"}</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link href="">제출</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link href={"/problems/" + problem.id + "/boards"}>게시판</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link href="">채점 현황</Nav.Link>
                </Nav.Item>
            </Nav>
            <Row>
                <Col md={6}>
                    <div className='page-header'>
                        <h1>{problem.title}</h1>
                    </div>
                    <table className='table'>
                        <thead>
                            <tr>
                                <th className='tablebody1'>시간제한</th>
                                <th className='tablebody1'>메모리 제한</th>
                                <th className='tablebody2'>제출</th>
                                <th className='tablebody2'>정답</th>
                                <th className='tablebody2'>맞힌 사람</th>
                                <th className='tablebody2'>정답 비율</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>{problem.timeLimit}</td>
                                <td>{problem.memoryLimit}</td>
                                <td>111</td>
                                <td>111</td>
                                <td>111</td>
                                <td>111</td>
                            </tr>
                        </tbody>

                    </table>
                    <div id='problem-body'>
                        <div>
                            <section>
                                <div className='headline'>
                                    <h2>문제</h2>
                                </div>
                                <div className='problem-text'>
                                    <p>{problem.content}</p>
                                </div>
                            </section>
                        </div>
                        <Row>
                            <Col md={6}>
                                <section>
                                    <div className='headline'>
                                        <h2>예제입력1</h2>
                                    </div>
                                    <pre className='sampledata'>
                                        1 2
                                    </pre>
                                </section>
                            </Col>
                            <Col md={6}>
                                <section>
                                    <div className='headline'>
                                        <h2>예제출력1</h2>
                                    </div>
                                    <pre className='sampledata'>
                                        3
                                    </pre>
                                </section>
                            </Col>
                        </Row>
                    </div>
                </Col>
                <Col md={6}>
                    <div className='page-header'>
                        <h1>입력창</h1>
                    </div>
                    <Form.Select aria-label="Default select example">
                        <option>Language</option>
                        <option value="1">Java</option>
                        <option value="2">C++</option>
                        <option value="3">Python</option>
                    </Form.Select>
                    <FloatingLabel controlId="floatingTextarea2" label="Comments">
                        <Form.Control
                            as="textarea"
                            placeholder="Leave a comment here"
                            style={{
                                height: '500px',
                                padding: '10px'
                            }}
                        />
                    </FloatingLabel>
                    <Col md={{ offset: 5, span: 4 }}>
                        <Button style={{ width: '50%' }}>제출</Button>
                    </Col>


                </Col>


            </Row>
        </div>}
    </div>;
}

export default ProblemDetail;
