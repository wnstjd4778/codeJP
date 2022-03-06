import React from 'react';
import { Button, Nav } from 'react-bootstrap';
import { Link, useParams } from 'react-router-dom';
import ProblemDetail from '../component/ProblemDetail';

function ProblemDetailPage() {
  const { problemId } = useParams();
  return <div>
    <Nav justify variant="tabs" defaultActiveKey="/home">
      <Nav.Item>
        <Nav.Link href="">{problemId + "번"}</Nav.Link>
      </Nav.Item>
      <Nav.Item>
        <Nav.Link href={"/problems/" + problemId + "/boards"}>게시판</Nav.Link>
      </Nav.Item>
      <Nav.Item>
        <Nav.Link href={"/problems/" + problemId + "/submits/me"}>채점 현황</Nav.Link>
      </Nav.Item>
    </Nav>

    <ProblemDetail />
  </div>;
}

export default ProblemDetailPage;
