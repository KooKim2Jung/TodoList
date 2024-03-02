import React, { useState, useEffect } from 'react';
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

    const addItem = async () => {
        try {
            const response = await fetch('http://localhost:8081/api/v1/todos', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    title: inputValue,
                    iscompleted: false 
                })
            });
            if (!response.ok) {
                throw new Error('Failed to add item.');
            }
            const newItem = await response.json();
            setTodoList([...todoList, newItem]);
            setInputValue('');
        } catch (error) {
            console.error('Error adding item:', error.message);
            setInputError('일정 추가에 실패했습니다.');
        }
    };

    const submitItem = async (event) => {
        resetForm();
        event.preventDefault();
        if (inputValue) {
            await addItem();
        } else {
            setInputError("추가된 일정이 없습니다.");
        }
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

    const removeAllItems = async (listType) => {
        try {
            let endpoint = 'http://localhost:8081/api/v1/todos';
            if (listType === "todoList") {
                endpoint += '?completed=false';
            } else if (listType === "completedList") {
                endpoint += '?completed=true';
            } else {
                return;
            }
    
            const response = await fetch(endpoint, {
                method: 'DELETE'
            });
    
            if (!response.ok) {
                throw new Error('Failed to delete items.');
            }
    
            if (listType === "todoList") {
                setTodoList([]);
            } else if (listType === "completedList") {
                setCompletedList([]);
            }
        } catch (error) {
            console.error('Error deleting items:', error.message);
            setInputError('일정 삭제에 실패했습니다.');
        }
    };

    useEffect(() => {
        const fetchPendingTodos = async () => {
            try {
                const response = await fetch('http://localhost:8081/api/v1/todos/pending');
                if (!response.ok) {
                    throw new Error('Failed to fetch pending todos.');
                }
                const data = await response.json();
                setTodoList(data);
            } catch (error) {
                console.error('Error fetching pending todos:', error.message);
                // 오류 처리
            }
        };

        const fetchCompletedTodos = async () => {
            try {
                const response = await fetch('http://localhost:8081/api/v1/todos/completed');
                if (!response.ok) {
                    throw new Error('Failed to fetch completed todos.');
                }
                const data = await response.json();
                setCompletedList(data);
            } catch (error) {
                console.error('Error fetching completed todos:', error.message);
                // 오류 처리
            }
        };


        fetchPendingTodos();
        fetchCompletedTodos();
    }, []);

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
