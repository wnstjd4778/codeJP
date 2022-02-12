import React from 'react';
import { Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';

function ProblemTableBody(props) {
    console.log("sdasd");
    const {problem} = props;
    const url = '/problems/' + problem.id;
    return <div>
        <tr>
            <td>{problem.id}</td>
            <td>{problem.title}</td>
            <td>{problem.category}</td>
            <td><Link to = {url}><Button>problem</Button></Link></td>
        </tr>
    </div>;
}

export default ProblemTableBody;
