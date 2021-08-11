import {withTranslation} from "react-i18next";
import {Helmet} from "react-helmet";
import "./resume.scss";
import {Component} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {Link, Redirect} from "react-router-dom";
import AboutMe from "./category/about-me";

class ResumeModifyComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            form:{
                options:{
                    menuItems: {'about-me': 'user-tie', 'education': 'university', 'languages': 'globe', 'experience': 'briefcase', 'skills': 'tools', 'websites-social-links': 'link', 'references': 'user-tag'}
                },
                ui:{
                    selectedMenuItem: ""
                }
            }
        };
    }

    componentDidMount() {
        this.setSelectedMenuItem(this.props.match.params.category);
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(this.props.match.params.category !== prevProps.match.params.category){
            this.setSelectedMenuItem(this.props.match.params.category);
        }
    }

    setSelectedMenuItem = (menuItem) =>{
        const {ui} = this.state.form;
        ui.selectedMenuItem = menuItem;
        this.setState({ui});
    }

    renderCategory = () =>{
        switch (this.props.match.params.category) {
            case 'about-me':
                return <AboutMe/>;
            default:
                return <Redirect to="/page-not-found" />;
        }
    }

    render(){
        const {t} = this.props;
        const {form} = this.state;

        return (
            <>
                <Helmet>
                    <title>{t("resume.page.title.add")}</title>
                </Helmet>

                <div className="container-lg pt-4">
                    <div className="row">
                        <div className="col-3">
                            <nav className="nav flex-column menu">
                                {Object.keys(form.options.menuItems).map(title =>
                                    <Link key={title} className={form.ui.selectedMenuItem === title ? 'nav-link menu-item selected' : 'nav-link menu-item'} to={`/resume/add/${title}`}>
                                        <span className="pr-2"><FontAwesomeIcon icon={form.options.menuItems[title]}/></span> {t(`resume.page.modify.menu.item.${title.replaceAll("-", ".")}.title`)}
                                    </Link>
                                )}
                            </nav>
                        </div>

                        <div className="col-9">
                            <div className="card">
                                <div className="card-body">
                                    {this.renderCategory()}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </>
        );
    }
}

export default withTranslation()(ResumeModifyComponent);
