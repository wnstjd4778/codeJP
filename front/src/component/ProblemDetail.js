import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const ProblemDetail = (props) => {


    const {id} = useParams();
    const [problem, setProblem] = useState();

    const getProblem = async () => {
        await axios.get('http://localhost:8080/problem/' + id)
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
        {problem === undefined ? "asdsa" :  <div>
       <h1>{problem.title}</h1><br/>
       <h1>{problem.content}</h1><br/>
       <h1>{problem.timeLimit}</h1><br/>
       <h1>{problem.memoryLimit}</h1><br/>
       <h1>{problem.category}</h1><br/>
       </div>}
       
    </div>;
}

export default ProblemDetail;
