import React from 'react';
import { Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';

function MyProblemTableBody(props) {
    const {problem} = props;
    const url = '/testcase/' + problem.id;
    return <div>
        <tr>
            <td>{problem.id}</td>
            <td>{problem.title}</td>
            <td>{problem.category}</td>
            <td><Link to = {url}><Button>testcase</Button></Link></td>
        </tr>
    </div>;
}

export default MyProblemTableBody;
