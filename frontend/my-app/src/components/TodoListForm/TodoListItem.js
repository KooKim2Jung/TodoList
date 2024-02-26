import React, { useState } from 'react';
import './TodoListForm.css';

const TodoListItem = ({ item, removeItem, completeItem, editItem }) => {
    const [isEditing, setIsEditing] = useState(false);
    const [editText, setEditText] = useState(item.text);

    const handleRemove = (event) => {
        event.stopPropagation(); // 클릭 이벤트 전파 방지
        removeItem(item.id);
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

    const handleSave = (event) => {
        event.stopPropagation(); // 이벤트 버블링 방지 추가
        editItem(item.id, editText); // 수정된 내용을 전달하여 editItem 호출
        setIsEditing(false); // 수정 모드 종료
        setEditText(editText); // 수정된 내용을 업데이트
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