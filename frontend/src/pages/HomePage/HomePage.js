import React from 'react';
import {Carousel} from "react-bootstrap";
import classes from './HomePage.module.css'

const HomePage = () => {
    return (
        <div className={classes.page}>
            <Carousel
                className={classes.carousel}
                data-bs-theme="dark">
                <Carousel.Item className={classes.carouselItem}>
                    <img
                     src='/img/1.png'
                     alt={"这里是个小区"}
                    />
                    <Carousel.Caption className={classes.itemText}>
                        <h3>智联未来</h3>
                        <p>共建新型智慧社区</p>
                    </Carousel.Caption>
                </Carousel.Item>
                <Carousel.Item>
                    {/*<ExampleCarouselImage text="Second slide" />*/}
                    <Carousel.Caption>
                        <h3>Second slide label</h3>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                    </Carousel.Caption>
                </Carousel.Item>
                <Carousel.Item>
                    {/*<ExampleCarouselImage text="Third slide" />*/}
                    <Carousel.Caption>
                        <h3>Third slide label</h3>
                        <p>
                            Praesent commodo cursus magna, vel scelerisque nisl consectetur.
                        </p>
                    </Carousel.Caption>
                </Carousel.Item>
            </Carousel>
        </div>
    );
};

export default HomePage;