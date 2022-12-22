import {Button, Card} from "react-bootstrap";
import Image from "next/image";

export default ({products}) => {
    return (
        products.map(p =>
            <Card key={p.id} className="mx-md-5">
                <Card.Title>{p.title}</Card.Title>
                <Card.Img src={p.imageUrl}/>
                <Card.Text>Great Product !</Card.Text>
                <Card.Footer>{p.price}
                    <Button className="mx-3 btn-info">
                        <Image alt="add to cart" width="30" height="30" src="/cart.svg"/>
                    </Button>
                </Card.Footer>
            </Card>
        )
    )
};
