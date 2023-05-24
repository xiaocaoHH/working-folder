function G=Get(Data1,Data2,fixData,span,Center,label)
% restruct datasets, alway put fixed point in the last position

count=1;
for i=1:length(Data1)
    for j=1:length(Data2)
        for t=1:length(fixData)
            x(count,1)=Data1(i);
            x(count,2)=Data2(j);
            x(count,3)=fixData(t);
            count=count+1;
        end
    end
end 

Wx=x(find(x(:,3)>Center(1,3)-span(1,3) & x(:,3)<Center(1,3) + span(1,3)),:);  % get new dataset in a certain range;

count=1;
for i=1:length(Center(:,1))          % get new center in a certain range;
    for j=1:length(Center(:,2))
        center_Input(count,1)=Center(i,1);
        center_Input(count,2)=Center(j,2);
        center_Input(count,3)=Center(1,3);
        count=count+1;
    end
end 

% get Hi under rules;
G=GetDegree(Wx,center_Input,span,label);