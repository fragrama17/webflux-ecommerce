import {Row} from "react-bootstrap";
import ProductCard from "./product-card";

const DUMMY_SALES = [
    {
        id: 1,
        title: "Slide",
        price: "129.99 $",
        imageUrl: "/dummy-products/slide-surfskate-fish-drifter-32.jpg",
    },
    {
        id: 4,
        title: "Olaian",
        price: "159.99 $",
        imageUrl: "/dummy-products/olaian-softboard.jpg"
    }
];

export default () => {
    return (
        <>
            <Row md={5} className="g-5 justify-content-center">
                <ProductCard products={DUMMY_SALES}/>
            </Row>
        </>
    );
}
