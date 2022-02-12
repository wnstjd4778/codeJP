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


function App() {
  return (
    <div>
      <Header />
      <Routes>
        <Route path='/users/login' element={<LoginPage />} />
        <Route path='/' element={<MainPage />} />
        <Route path='/problems/insert' element={<InsertProblemPage />} />
        <Route path="/problems" element={<ProblemListPage/>} />
        <Route path="/problems/:id" element={<ProblemDetailPage/>} />
        <Route path="/problems/me" element={<MyProblemPage/>} />
      </Routes>
    </div>
  );
}

export default App;
