import React from 'react';

const TestcaseTableBody = (props) => {
    const {testcase} = props;
    return <div>
        <tr>
            <td>{testcase.id}</td>
            <td>{testcase.parameter}</td>
            <td>{testcase.expectedData}</td>
        </tr>
    </div>;
};

export default TestcaseTableBody;