import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ProblemDetail = (props) => {


    console.log(props);
    
    const [problem, setProblem] = useState();

    const getProblem = async () => {
        await axios.get('http://localhost:8080/problem')
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
       <h1>{problem.title}</h1><br/>
       <h1>{problem.content}</h1><br/>
       <h1>{problem.timeLimit}</h1><br/>
       <h1>{problem.memoryLimit}</h1><br/>
       <h1>{problem.category}</h1><br/>
    </div>;
}

export default ProblemDetail;
