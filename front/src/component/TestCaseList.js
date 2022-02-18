import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Col, Table } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import TestcaseTableBody from './TestcaseTableBody';

const TestCaseList = () => {

    const [testcases, setTestcases] = useState();
    const { problemId } = useParams();

    const getTestcases = async () => {
        axios.get("http://localhost:8080/testcase/" + problemId)
            .then(res => {
                setTestcases(res.data);
            })
            .catch(err => {
                alert(err);
            })
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
        </div>
    );
};

export default TestCaseList;