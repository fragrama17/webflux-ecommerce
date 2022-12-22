import {Row} from "react-bootstrap";
import ProductCard from "../../components/product-card";
import LandingPage from "../index";
import BgVideo from "../../components/bg-video";

const DUMMY_PRODUCTS = [
    {
        id: 1,
        title: "Slide",
        price: "129.99 $",
        imageUrl: "/dummy-products/slide-surfskate-fish-drifter-32.jpg",
    },
    {
        id: 2,
        title: "Smooth-Star Toledo",
        price: "329.99 $",
        imageUrl: "/dummy-products/smoothstar-filipe-toledo.jpg"
    },
    {
        id: 3,
        title: "Smooth-Star Holy Toledo",
        price: "349.99 $",
        imageUrl: "/dummy-products/smoothstar-holytoledo-surfskate.jpg"
    },
    {
        id: 4,
        title: "Olaian",
        price: "159.99 $",
        imageUrl: "/dummy-products/olaian-softboard.jpg"
    },
    {
        id: 5,
        title: "Duotone Wam",
        price: "689.99 $",
        imageUrl: "/dummy-products/duotone-wam-surfboard.jpg"
    }
];

export default () => {
    return (
        <>
            <BgVideo/>
            <Row md={5} className="g-5 justify-content-center">
                <ProductCard products={DUMMY_PRODUCTS}/>
            </Row>
        </>
    );
}
