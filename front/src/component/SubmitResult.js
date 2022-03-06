import React, { useEffect, useState } from 'react';
import { Col, Table } from 'react-bootstrap';

const SubmitResult = () => {

    const [submitResults, setSubmitResuls] = useState();

    const getSubmits = async() => {

    }

    useEffect(() => {

    })

    return (
        <div>
            <Col md={{ span: 8, offset: 2 }}>
                <Table striped bordered hover size="sm">
                    <thead>
                        <tr>
                            <th>index</th>
                            <th>language</th>
                            <th>problemId</th>
                            <th>status</th>
                        </tr>
                    </thead>
                    <tbody>
                        {submitResults === undefined ? "sadas" : submitResults.map((submitResult, index) => {
                            return (
                            <tr>
                                <td>{submitResult.index}</td>
                                <td>{submitResult.language}</td>
                                <td>{submitResult.problem.id}</td>
                                <td>{submitResult.status}</td>
                            </tr>
                            )
                        })}
                    </tbody>
                </Table>
            </Col>
        </div>
    );
};

export default SubmitResult;