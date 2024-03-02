import React from 'react';
import TodoListItem from './TodoListItem';
import './TodoListForm.css';

const TodoListBoard = ({ todoList, removeItem, completeItem, revertItem, editItem }) => {
    return (
        <div>
            {Array.isArray(todoList) && todoList.map((item) => (
                <div key={item.id} onClick={() => completeItem ? completeItem(item.id) : revertItem(item.id)}>
                    <TodoListItem item={item} removeItem={() => removeItem(item.id)} completeItem={completeItem} editItem={editItem} />
                </div>
            ))}
        </div>
    );
};

export default TodoListBoard;