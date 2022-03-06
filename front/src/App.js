import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import InsertProblemPage from './page/InsertProblemPage';
import { Route, Routes } from 'react-router-dom';
import LoginPage from './page/LoginPage';
import MainPage from './page/MainPage';
import Header from './component/Header';
import ProblemListPage from './page/ProblemListPage';
import ProblemDetailPage from './page/ProblemDetailPage';
import MyProblemPage from './page/MyProblemPage';
import ShowTestcasePage from './page/ShowTestcasePage';
import ProblemBoardPage from './page/ProblemBoardPage';
import BoardDetailPage from './page/BoardDetailPage';
import MyInfoPage from './page/MyInfoPage';
import SubmitResultPage from './page/SubmitResultPage';


function App() {
  return (
    <div>
      <Header />
      <Routes>
        <Route path='/users/login' element={<LoginPage />} />
        <Route path='/' element={<MainPage />} />
        <Route path='/problems/insert' element={<InsertProblemPage />} />
        <Route path="/problems" element={<ProblemListPage/>} />
        <Route path="/problems/:problemId" element={<ProblemDetailPage/>} />
        <Route path="/problems/me" element={<MyProblemPage/>} />
        <Route path="/testcase/:problemId" element={<ShowTestcasePage/>} />
        <Route path="/problems/:problemId/boards" element={<ProblemBoardPage/>} />
        <Route path="/problems/:problemId/boards/:boardId" element={<BoardDetailPage/>} />
        <Route path="/users/myPage" element={<MyInfoPage/>} />
        <Route path="/problems/:problemId/submits/me" element={<SubmitResultPage/>} />
      </Routes>
    </div>
  );
}

export default App;
