import React, { useState } from 'react';
import './TodoListForm.css';

const TodoListItem = ({ item, removeItem, completeItem, editItem }) => {
    const [isEditing, setIsEditing] = useState(false);
    const [editText, setEditText] = useState(item.text);

    const handleRemove = async (event) => {
        event.stopPropagation(); // 클릭 이벤트 전파 방지
        try {
            const response = await fetch(`http://localhost:8081/api/v1/todos/${item.id}`, {
                method: 'DELETE'
            });
            if (!response.ok) {
                throw new Error('Failed to delete item.');
            }
            removeItem(item.id);
        } catch (error) {
            console.error('Error deleting item:', error.message);
            // 오류 발생 시 필요한 처리 작업 추가
        }
    };

    const handleClick = () => {
        if (completeItem && !isEditing) {
            completeItem(item.id);
        }
    };

    const handleEdit = (event) => {
        event.stopPropagation(); // 이벤트 버블링 방지
        setIsEditing(true);
    };

    const handleSave = async (event) => {
        event.stopPropagation(); // 이벤트 버블링 방지 추가
        try {
            const response = await fetch(`http://localhost:8081/api/v1/todos/${item.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    title: editText
                })
            });
            if (!response.ok) {
                throw new Error('Failed to save item.');
            }
            editItem(item.id, editText); // 수정된 내용을 전달하여 editItem 호출
            setIsEditing(false); // 수정 모드 종료
        } catch (error) {
            console.error('Error saving item:', error.message);
            // 오류 발생 시 필요한 처리 작업 추가
        }
    };

    const handleInputChange = (event) => {
        setEditText(event.target.value);
    };

    const handleInputClick = (event) => {
        event.stopPropagation();
    };

    return (
        <div className='todo-box' onClick={handleClick}>
            {isEditing ? (
                <div className='input-box'>
                    <input
                        type="text"
                        value={editText}
                        onChange={handleInputChange}
                        onClick={handleInputClick}
                    />
                    <button className='save-btn' type='button' onClick={handleSave}>저장</button>
                </div>
            ) : (
                <div className='container'>
                    <div className='todo-list'>{item.text}</div>
                    <button className='edit-btn' onClick={handleEdit}>수정</button>
                    <button className='del-btn' onClick={handleRemove}>삭제</button>
                </div>
            )}
        </div>
    );
};

export default TodoListItem;