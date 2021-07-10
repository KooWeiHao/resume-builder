import {Dropdown} from "react-bootstrap";
import {useState} from "react";

function DropdownPersist(props){
    const [open, setOpen] = useState(false);
    const onToggle = (isOpen, ev, metadata) => {
        if (metadata.source === "select") {
            setOpen(true);
            return;
        }
        setOpen(isOpen);
    };

    return <Dropdown show={open} onToggle={onToggle} {...props}/>;
}

export default DropdownPersist;
