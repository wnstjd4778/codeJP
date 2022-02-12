import React from 'react';
import { Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';

function ProblemTableBody(props) {
    const {problem, index} = props;

    return <div>
        <tr>
            <td>{index}</td>
            <td>{problem.title}</td>
            <td>{problem.category}</td>
            <td><Link to = '/problems/'><Button>problem</Button></Link></td>
        </tr>
    </div>;
}

export default ProblemTableBody;
