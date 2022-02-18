import React from 'react';
import { Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import MyProblemList from '../component/MyProblemList';

const MyProblemPage = () => {
    return (
        <div>
            <MyProblemList/>
        </div>
    );
};

export default MyProblemPage;