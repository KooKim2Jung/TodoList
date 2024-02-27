import React, { useState } from 'react';
import TodoListBoard from './TodoListBoard';
import TodoList from '../../pages/TodoList';
import './TodoListForm.css';

const TodoListForm = () => {
    const [inputValue, setInputValue] = useState('');
    const [todoList, setTodoList] = useState([]);
    const [completedList, setCompletedList] = useState([]);
    const [inputError, setInputError] = useState('');

    const resetForm = () => {
        setInputError('');
    };

    const submitItem = (event) => {
        resetForm();
        event.preventDefault();
        inputValue ? addItem(inputValue) : setInputError("추가된 일정이 없습니다.");
    };

    const addItem = () => {
        const newItem = {
            id: Date.now(),
            text: inputValue,
        };
        setTodoList([...todoList, newItem]);
        setInputValue('');
    };

    const removeItem = (id) => {
        setTodoList(todoList.filter((item) => item.id !== id));
        setCompletedList(completedList.filter((item) => item.id !== id));
    };

    const completeItem = (id) => {
        const completedItem = todoList.find((item) => item.id === id);
        if (completedItem) {
            setCompletedList([...completedList, completedItem]);
            setTodoList(todoList.filter((item) => item.id !== id));
        }
    };

    const revertItem = (id) => {
        const revertedItem = completedList.find((item) => item.id === id);
        if (revertedItem) {
            setTodoList([...todoList, revertedItem]);
            setCompletedList(completedList.filter((item) => item.id !== id));
        }
    };

    const editItem = (id, newText) => {
        const updatedTodoList = todoList.map(item => {
            if (item.id === id) {
                return {...item, text: newText};
            }
            return item;
        });
        setTodoList(updatedTodoList);
    
        const updatedCompletedList = completedList.map(item => {
            if (item.id === id) {
                return {...item, text: newText};
            }
            return item;
        });
        setCompletedList(updatedCompletedList);
    };

    const removeAllItems = (listType) => {
        if (listType === "todoList") {
            setTodoList([]);
        } else if (listType === "completedList") {
            setCompletedList([]);
        }
    };

    return (
        <div className="TodoList">
            <TodoList/>
            <form onSubmit={submitItem}>
                <div className='errormsg'>{inputError}</div>
                <div className='input-box'>
                    <input
                        type="text"
                        value={inputValue}
                        onChange={(event) => setInputValue(event.target.value)}
                        placeholder='일정을 입력해 주세요.'
                    />
                    <button className="add-btn">추가</button>
                </div>
                <div className='todo-board'>
                    <div className='todo-underdone'>
                        <h2>할 일</h2>
                        <TodoListBoard todoList={todoList} removeItem={removeItem} completeItem={completeItem} editItem={editItem} />
                        <button onClick={() => removeAllItems("todoList")}>전체 삭제</button>
                    </div>
                    <div className='todo-done'>
                        <h2>완료된 항목</h2>
                        <TodoListBoard todoList={completedList} removeItem={removeItem} revertItem={revertItem} editItem={editItem} />
                        <button onClick={() => removeAllItems("completedList")}>전체 삭제</button>
                    </div>
                </div>
            </form>
        </div>
    );
};

export default TodoListForm;
