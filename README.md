import {
  createBrowserRouter,
  Navigate,
  RouterProvider,
} from 'react-router-dom';

import { PageTitle, RedirectToHome, RedirectToLogin } from './RouteGuards';
import AppLayout from '../shared/components/layout/AppLayout';
import ExtranetMenu from '../apps/extranetMenu/pages';
import ClientsList from '../apps/pcnAdmin/pages/Clients/List';
import ClientsCreate from '../apps/pcnAdmin/pages/Clients/Create';
import ClientsEdit from '../apps/pcnAdmin/pages/Clients/Edit';
import SubscriptionsList from '../apps/pcnAdmin/pages/Subscriptions/List';
import SubscriptionsCreate from '../apps/pcnAdmin/pages/Subscriptions/Create';
import SubscriptionsEdit from '../apps/pcnAdmin/pages/Subscriptions/Edit';
import CheckersList from '../apps/pcnAdmin/pages/Checkers/List';
import CheckersCreate from '../apps/pcnAdmin/pages/Checkers/Create';
import CheckersEdit from '../apps/pcnAdmin/pages/Checkers/Edit';
import PublishersList from '../apps/pcnAdmin/pages/Publishers/List';
import PublishersCreate from '../apps/pcnAdmin/pages/Publishers/Create';
import PublishersEdit from '../apps/pcnAdmin/pages/Publishers/Edit';
import ElementList from '../apps/webGetDataAdmin/pages/Elements/List';
import ElementsCreate from '../apps/webGetDataAdmin/pages/Elements/Create/ElementDetails';
import ElementsEdit from '../apps/webGetDataAdmin/pages/Elements/Edit/ElementDetails';
import ServiceKeyList from '../apps/webGetDataAdmin/pages/ServiceKeys/List';
import ServiceKeysCreate from '../apps/webGetDataAdmin/pages/ServiceKeys/Create';
import ServiceKeysEdit from '../apps/webGetDataAdmin/pages/ServiceKeys/Edit/ServiceKeysEdit';
import ElementServiceKeyCreate from '../apps/webGetDataAdmin/pages/Elements/Create/ElementServiceKeyMapping';
import ElementResultFieldCreate from '../apps/webGetDataAdmin/pages/Elements/Create/ResultFields';
import ElementResultFieldEdit from '../apps/webGetDataAdmin/pages/Elements/Edit/ResultFields';
import ElementServiceKeyEdit from '../apps/webGetDataAdmin/pages/Elements/Edit/ElementServiceKeyMapping';
import ElementServiceKeyMappingEdit from '../apps/webGetDataAdmin/pages/Elements/Edit/ServiceKeyMapping';
import ElementResultFieldMappingEdit from '../apps/webGetDataAdmin/pages/Elements/Edit/ResultFieldMapping';
import ElementsCreateRoot from '../apps/webGetDataAdmin/pages/Elements/Create';
import ElementsEditRoot from '../apps/webGetDataAdmin/pages/Elements/Edit';
import ServiceKeyMapped from '../apps/webGetDataAdmin/pages/ServiceKeys/Edit/ServiceKeyMapped';
import ServiceKeyEditRoot from '../apps/webGetDataAdmin/pages/ServiceKeys/Edit';
import ElementServiceKeyMappingCreate from '../apps/webGetDataAdmin/pages/Elements/Edit/CreateServicekeyMap';
import ElementResultFieldMappingCreate from '../apps/webGetDataAdmin/pages/Elements/Edit/CreateResultFieldMap';
import ReportsFavourites from '../apps/reports/pages/Favourites';
import ReportsViewAll from '../apps/reports/pages/ViewAll';
import MainProcessFile from '../apps/webGetData/pages/ProcessFile/MainProcessFile';
import MainProcessFileResults from '../apps/webGetData/pages/ProcessFile/ProcessFileResults/MainProcessFileResults';
import FileUpload from '../apps/fileManager/pages/Upload';
import FileList from '../apps/fileManager/pages/List/Index';
import MainInteractive from '../apps/webGetData/pages/Interactive/MainInteractive';

const router = createBrowserRouter([
  {
    path: '',
    // ErrorBoundary: null,
    children: [
      {
        path: '/',
        element: <AppLayout />,
        children: [
          {
            index: true,
            element: <RedirectToHome />,
          },
          {
            path: 'extranet-menu/:appId?',
            caseSensitive: true,
            element: (
              <PageTitle title={'Extranet Menu'}>
                <ExtranetMenu />
              </PageTitle>
            ),
          },
          {
            path: ':appId',
            children: [
              {
                index: true,
                element: <RedirectToHome />,
              },
              {
                path: 'reports',
                caseSensitive: true,
                children: [
                  {
                    index: true,
                    element: <Navigate to="./view-all" />,
                  },
                  {
                    path: 'view-all',
                    caseSensitive: true,
                    element: (
                      <>
                        <PageTitle title={'Web-Get-Data Interactive'}>
                          <ReportsViewAll />
                        </PageTitle>
                      </>
                    ),
                  },
                  {
                    path: 'favourites',
                    caseSensitive: true,
                    element: (
                      <>
                        <PageTitle title={'Web-Get-Data Process-File'}>
                          <ReportsFavourites />
                        </PageTitle>
                      </>
                    ),
                  },
                ],
              },
              {
                path: 'web-getdata',
                caseSensitive: true,

                children: [
                  {
                    index: true,
                    element: <Navigate to="./interactive" />,
                  },
                  {
                    path: 'interactive',
                    caseSensitive: true,
                    element: (
                      <>
                          <PageTitle title={'Web-Get-Data Interactive'}>
                            <MainInteractive/>
                          </PageTitle>
                      </>
                    ),
                  },
                  {
                    path: 'process-file',
                    caseSensitive: true,
                    element: (
                      <>
                        <PageTitle title={'Web-Get-Data Process-File'}>
                          <MainProcessFile />
                        </PageTitle>
                      </>
                    ),
                  },
                  {
                    path: 'process-file-results',
                    caseSensitive: true,
                    element: (
                      <>
                        <PageTitle title={'Web-Get-Data Process-File-Results'}>
                          <MainProcessFileResults />
                        </PageTitle>
                      </>
                    ),
                  },
                ],
              },
              {
                path: 'web-getdata-admin',
                caseSensitive: true,
                children: [
                  {
                    index: true,
                    element: <Navigate to="./elements" />,
                  },
                  {
                    path: 'elements',
                    caseSensitive: true,
                    children: [
                      {
                        index: true,
                        element: (
                          <PageTitle title={'Elements - Web GetData Admin'}>
                            <ElementList />
                          </PageTitle>
                        ),
                      },
                      {
                        path: 'create',
                        caseSensitive: true,
                        element: <ElementsCreateRoot />,
                        children: [
                          {
                            index: true,
                            element: (
                              <PageTitle
                                title={
                                  'Create Elements Details - Web GetData Admin'
                                }
                              >
                                <ElementsCreate />
                              </PageTitle>
                            ),
                          },

                          {
                            path: 'serviceKeyMap',
                            caseSensitive: true,
                            element: (
                              <PageTitle
                                title={
                                  'Create Elements ServiceKey Map- Web GetData Admin'
                                }
                              >
                                <ElementServiceKeyCreate />
                              </PageTitle>
                            ),
                          },
                          {
                            path: 'resultFieldMap',
                            caseSensitive: true,
                            element: (
                              <PageTitle
                                title={
                                  'Create Result Field Map - Web GetData Admin'
                                }
                              >
                                <ElementResultFieldCreate />
                              </PageTitle>
                            ),
                          },
                        ],
                      },
                      {
                        path: 'edit/:elementId',
                        caseSensitive: true,
                        element: <ElementsEditRoot />,
                        // element: (
                        //   <PageTitle
                        //     title={'Edit Elements - Web GetData Admin'}
                        //   >
                        //     <ElementsEdit />
                        //   </PageTitle>
                        // ),
                        children: [
                          {
                            index: true,
                            element: (
                              <PageTitle
                                title={'Edit Elements - Web GetData Admin'}
                              >
                                <ElementsEdit />
                              </PageTitle>
                            ),
                          },

                          {
                            path: 'serviceKeyMap',
                            caseSensitive: true,
                            children: [
                              {
                                index: true,

                                element: (
                                  <PageTitle
                                    title={
                                      'Edit ServiceKey Map- Web GetData Admin'
                                    }
                                  >
                                    <ElementServiceKeyEdit />
                                  </PageTitle>
                                ),
                              },
                              {
                                path: ':serviceKeyId',
                                caseSensitive: true,
                                element: (
                                  <PageTitle
                                    title={
                                      'Create Edit ServiceKey Map - Web GetData Admin'
                                    }
                                  >
                                    <ElementServiceKeyMappingEdit />
                                  </PageTitle>
                                ),
                              },
                              {
                                path: 'create',
                                caseSensitive: true,
                                element: (
                                  <PageTitle
                                    title={
                                      'Create ServiceKey Map - Web GetData Admin'
                                    }
                                  >
                                    <ElementServiceKeyMappingCreate />
                                  </PageTitle>
                                ),
                              },
                            ],
                          },
                          {
                            path: 'resultFieldMap',
                            caseSensitive: true,
                            children: [
                              {
                                index: true,
                                element: (
                                  <PageTitle
                                    title={
                                      'Create Result Field Map - Web GetData Admin'
                                    }
                                  >
                                    <ElementResultFieldEdit />
                                  </PageTitle>
                                ),
                              },
                              {
                                path: ':fieldIndex',
                                caseSensitive: true,
                                element: (
                                  <PageTitle
                                    title={
                                      'Create Edit ServiceKey Map - Web GetData Admin'
                                    }
                                  >
                                    <ElementResultFieldMappingEdit />
                                  </PageTitle>
                                ),
                              },
                              {
                                path: 'create',
                                caseSensitive: true,
                                element: (
                                  <PageTitle
                                    title={
                                      'Create ResultField - Web GetData Admin'
                                    }
                                  >
                                    <ElementResultFieldMappingCreate />
                                  </PageTitle>
                                ),
                              },
                            ],
                          },
                        ],
                      },
                    ],
                  },
                  {
                    path: 'serviceKeys',
                    caseSensitive: true,
                    children: [
                      {
                        index: true,
                        element: (
                          <PageTitle title={'ServiceKeys - Web GetData Admin'}>
                            <ServiceKeyList />
                          </PageTitle>
                        ),
                      },
                      {
                        path: 'create',
                        caseSensitive: true,
                        element: (
                          <PageTitle
                            title={'Create ServiceKeys - Web GetData Admin'}
                          >
                            <ServiceKeysCreate />
                          </PageTitle>
                        ),
                      },
                      {
                        path: 'edit/:id',
                        caseSensitive: true,
                        element: <ServiceKeyEditRoot />,
                        children: [
                          {
                            index: true,
                            element: (
                              <PageTitle
                                title={'Edit Servicekey - Web GetData Admin'}
                              >
                                <ServiceKeysEdit />
                              </PageTitle>
                            ),
                          },
                          {
                            path: 'serviceKeyMapped',
                            caseSensitive: true,
                            element: (
                              <PageTitle
                                title={
                                  'Servicekey Mapped list - Web GetData Admin'
                                }
                              >
                                <ServiceKeyMapped />
                              </PageTitle>
                            ),
                          },
                        ],
                      },
                    ],
                  },
                  {
                    path: 'preset',
                    caseSensitive: true,
                  },
                ],
              },
              {
                path: 'pcn-admin',
                caseSensitive: true,
                children: [
                  {
                    index: true,
                    element: <Navigate to="./clients" />,
                  },
                  {
                    path: 'clients',
                    caseSensitive: true,
                    children: [
                      {
                        index: true,
                        element: (
                          <PageTitle title={'Clients - PCN Admin'}>
                            <ClientsList />
                          </PageTitle>
                        ),
                      },
                      {
                        path: 'create',
                        caseSensitive: true,
                        element: (
                          <PageTitle title={'Create Client - PCN Admin'}>
                            <ClientsCreate />
                          </PageTitle>
                        ),
                      },
                      {
                        path: 'edit/:id',
                        caseSensitive: true,
                        element: (
                          <PageTitle title={'Edit Client - PCN Admin'}>
                            <ClientsEdit />
                          </PageTitle>
                        ),
                      },
                    ],
                  },
                  {
                    path: 'subscriptions',
                    caseSensitive: true,
                    children: [
                      {
                        index: true,
                        element: (
                          <PageTitle title={'Subscriptions - PCN Admin'}>
                            <SubscriptionsList />
                          </PageTitle>
                        ),
                      },
                      {
                        path: 'create',
                        caseSensitive: true,
                        element: (
                          <PageTitle title={'Create Subscription - PCN Admin'}>
                            <SubscriptionsCreate />
                          </PageTitle>
                        ),
                      },
                      {
                        path: 'edit/:id',
                        caseSensitive: true,
                        element: (
                          <PageTitle title={'Edit Subscription - PCN Admin'}>
                            <SubscriptionsEdit />
                          </PageTitle>
                        ),
                      },
                    ],
                  },
                  {
                    path: 'checkers',
                    caseSensitive: true,
                    children: [
                      {
                        index: true,
                        element: (
                          <PageTitle title={'Checkers - PCN Admin'}>
                            <CheckersList />
                          </PageTitle>
                        ),
                      },
                      {
                        path: 'create',
                        caseSensitive: true,
                        element: (
                          <PageTitle title={'Create Checker - PCN Admin'}>
                            <CheckersCreate />
                          </PageTitle>
                        ),
                      },
                      {
                        path: 'edit/:clientId/:subscriptionId',
                        caseSensitive: true,
                        element: (
                          <PageTitle title={'Edit Checker - PCN Admin'}>
                            <CheckersEdit />
                          </PageTitle>
                        ),
                      },
                    ],
                  },
                  {
                    path: 'publishers',
                    caseSensitive: true,
                    children: [
                      {
                        index: true,
                        element: (
                          <PageTitle title={'Publishers - PCN Admin'}>
                            <PublishersList />
                          </PageTitle>
                        ),
                      },
                      {
                        path: 'create',
                        caseSensitive: true,
                        element: (
                          <PageTitle title={'Create Publisher - PCN Admin'}>
                            <PublishersCreate />
                          </PageTitle>
                        ),
                      },
                      {
                        path: 'edit/:clientId/:subscriptionId',
                        caseSensitive: true,
                        element: (
                          <PageTitle title={'Edit Publisher - PCN Admin'}>
                            <PublishersEdit />
                          </PageTitle>
                        ),
                      },
                    ],
                  },
                ],
              },
              {
                path: 'file-manager',
                caseSensitive: true,
                children: [
                  {
                    index: true,
                    element: (
                      <PageTitle title={'File List - File Manager'}>
                        <FileList />
                      </PageTitle>
                    ),
                  },
                  {
                    path: 'upload',
                    caseSensitive: true,
                    element: (
                      <PageTitle title={'File Upload - File Manager'}>
                        <FileUpload />
                      </PageTitle>
                    ),
                  },
                ],
              },
            ],
          },
        ],
      },
    ],
  },
  {
    path: '*',
    element: <RedirectToLogin />,
  },
]);

export default function Router() {
  return <RouterProvider router={router} />;
}
